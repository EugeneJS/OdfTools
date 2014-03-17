package com.company;

import com.rest.App;
import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignature;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.meta.Meta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;


public class WatchDir {

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace = false;
    private String key = "EugId";
    private String metaDocId;
    private String certId;
    //private String signers[];
    TextDocument doc;
    private App restApp = new App();
    private ArrayList<String> certs = new ArrayList<String>();


    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Задает данную дирикторию для наблюдения WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("Задана директория: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("Обновлено: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Задает данную дирикторию и все ее поддиректории для наблюдения WatchService
     */
    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Создает WatchService и задает данную дирикторию
     */
    WatchDir(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;

        if (recursive) {
            System.out.format("Сканирую %s ...\n", dir);
            registerAll(dir);
            System.out.println("Готово.");
        } else {
            register(dir);
        }

        // включить след после первоначальной регистрации
        this.trace = true;
    }

    /**
     * Процес всех события для очереди ключей в watcher
     */
    void processEvents(){
        for (;;) {

            boolean preventDoubleMessage = false;

            // ждем ключ, который будет сигналом
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey не опознан!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // Событие, которое произошло в папке - название папки
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);


                if (event.kind().name() == "ENTRY_MODIFY") {

                    if (!child.toString().endsWith(".odt*")    // Исключаем сообщение об изменении временных файлов
                            && !child.toString().endsWith(".DS_Store") && (child.getFileName().toString() != ".DS_Store")
                            && !preventDoubleMessage) {          // И исключаем повторное сообщение о файле
                        System.out.format("Изменен: %s\n %s", child, child.getFileName().toString());
                    }

                    if (child.toString().endsWith(".odt")) {
                        System.out.println("---Изменен файл формата .odt!---");
                    }
                }

                if (event.kind().name() == "ENTRY_DELETE") {

                    if (!child.toString().endsWith(".odt#")     // Исключаем сообщение об удалении временных файла
                            || !child.toString().endsWith(".DS_Store"))
                        System.out.format("Удален: %s\n", child);

                }

                if (event.kind().name() == "ENTRY_CREATE") {

                    if (!child.toString().endsWith(".odt#")   // Исключаем сообщение об создании временных файла
                            || !child.toString().endsWith(".DS_Store"))
                        System.out.format("Добавлен: %s\n", child);

                    if (child.toString().endsWith(".odt")) {
                        System.out.println("---Добавлен файл формата .odt!---");
                        checkSignature(child.toString());
                        if (checkMetaExist(child)) {
                            preventDoubleMessage = true;
                        }

                        try {
                            restApp.DrupalRestClient(child.toString(), child.getFileName().toString(), metaDocId, "Here will be body", certId, certs);
                            //restApp.DrupalRestClient(child.toString(), child.getFileName().toString(), metaDocId, "Here will be body", signers);
                        }  catch (Exception e){

                        }

                    }
                }


                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                }
            }

            // сбросить ключ и удалить из набора, если каталог больше не доступен
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // все каталоги недоступны
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.CreateUsersHashMap();
        boolean recursive = true;

        Path dir = Paths.get("/Users/Desmond/Desktop/DreamDir");
        new WatchDir(dir, recursive).processEvents();
    }

    private boolean checkMetaExist(Path file) {

        try {
            doc = TextDocument.loadDocument(file.toString());

            //System.out.println(doc.getMetaDom());

            OdfFileDom metadom = doc.getMetaDom();

            Meta metadata = new Meta(metadom);

            String dataValue = metadata.getUserDefinedDataValue(key);

            if (dataValue != null)
            {
                System.out.println("У нас есть поле EugId!");
                System.out.println("Его значение: " + dataValue);
                metaDocId = dataValue;
                return true;
            }
            else
            {
                System.out.println("Поля EugId нет!");
                metadata.setUserDefinedData(key, "Text", "myIdFromCode");
                System.out.println("Создали новое поле");
                dataValue = metadata.getUserDefinedDataValue(key);
                doc.save(file.toString());
                System.out.println("Сохранили документ");
                metaDocId = dataValue;
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void checkSignature(String path){
        try {

            OdfTextDocument td = OdfTextDocument.loadDocument(path);
            Document dom = td.getPackage().getDom("META-INF/documentsignatures.xml");

            //String nodeName = dom.getNodeName();
            Node docSigs = dom.getChildNodes().item(0);
            Node sig;
            XMLSignature digsig;

            System.out.println("-----Документ подписан:-----");
            for (int i = 0; i < docSigs.getChildNodes().getLength(); i++){
                sig = docSigs.getChildNodes().item(i);

                Init.init();
                digsig  = new XMLSignature((Element) sig, "Signature");
                String subjectText = digsig.getKeyInfo().getX509Certificate().getSubjectDN().getName().substring(3);

                if (subjectText.indexOf(",") != -1) {  // Проверяем есть ли что то еще кроме имени подписанта в subject
                    System.out.println(subjectText.substring(0, subjectText.indexOf(",")));
                    //X509Certificate certToVerify = digsig.getKeyInfo().getX509Certificate();
                    certId = digsig.getKeyInfo().getX509Certificate().getSerialNumber().toString();
                    System.out.println(certId);
                    certs.add(certId);
                    //cert.checkValidity();                                        не совсем ясно с варификацией, что
                    //certToVerify.verify(digsig.getKeyInfo().getPublicKey());     то из этих функций должно помочь
                    //System.out.println(digsig.getSignedInfo().verify());
                }
                else {
                    System.out.println(subjectText);
                    //X509Certificate cert = digsig.getKeyInfo().getX509Certificate();
                    certId = digsig.getKeyInfo().getX509Certificate().getSerialNumber().toString();
                    System.out.println(certId);
                    certs.add(certId);
                    //cert.checkValidity();
                }

            }
            System.out.println("-------------------------");




        } catch (Exception e) {
            System.err.println("ERROR from checkSignature()");
        }
    }
}