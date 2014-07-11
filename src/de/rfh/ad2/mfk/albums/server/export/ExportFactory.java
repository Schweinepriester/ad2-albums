package de.rfh.ad2.mfk.albums.server.export;

/**
 * Created by MFK on 03.07.2014.
 */
public class ExportFactory {

    public enum ExportType{JSON,CSV,XML}

    public static ExportService getInstance(ExportType exportType){
        ExportService exportService = null;
        switch (exportType){
            case JSON: exportService = new Export2JSON(); break;
            case CSV: exportService = new Export2CSV(); break;
            case XML: exportService = new Export2XML(); break;
            default:
                // TODO throw real exception
                System.out.println("no such ExportService");
                break;
        }
        return exportService;
    }

}
