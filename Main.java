import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

  public static void main(String[] args) throws IIOException {

    GameProgress progressOne = new GameProgress(100, 100, 100, 10.10);
    GameProgress progressTwo = new GameProgress(200, 200, 200, 20.20);
    GameProgress progressThree = new GameProgress(300, 300, 300, 30.30);
    String constPath = "C://Games/savegames/";

    saveGame(constPath + "saveOne.dat", progressOne);
    saveGame(constPath + "saveTwo.dat", progressTwo);
    saveGame(constPath + "saveThree.dat", progressThree);

    zipFiles(constPath + "save.zip", constPath);

    delNotZip(constPath);

  }

  static void saveGame(String path, GameProgress gameProgress) throws IIOException {

    try {
      new File(path).createNewFile();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try (FileOutputStream fos = new FileOutputStream(path);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(gameProgress);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  static void zipFiles(String pathZip, String pathDat) {
    File dir = new File(pathDat);
    List<File> list = new ArrayList<>();
    for (File file : dir.listFiles()) {
      if (file.isFile() && !file.getName().endsWith("zip")) {
        list.add(file);
      }
    }

    try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathZip))) {
      for (File f : list) {
        try (FileInputStream fis = new FileInputStream(f)) {
          ZipEntry zipEntry = new ZipEntry(f.getName());
          zos.putNextEntry(zipEntry);
          byte[] buffer = new byte[fis.available()];
          fis.read(buffer);
          zos.write(buffer);
          zos.closeEntry();
        } catch (Exception exception) {
          System.out.println(exception.getMessage());
        }
      }
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
    }
  }

  static void delNotZip(String constPath) {
    for (File fileDir : new File(constPath).listFiles()) {
      if (fileDir.getName().endsWith("dat")) {
        fileDir.delete();
      }
    }
  }
}
