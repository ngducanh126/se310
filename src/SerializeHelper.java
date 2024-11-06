import java.io.*;

public class SerializeHelper {

    // Generic method to deserialize an object from a file
    public static <T> T deserialize(Class<T> type, String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            return type.cast(obj);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generic method to serialize an object to a file
    public static <T> String serialize(Class<T> type, Object obj, String dirPath, String fileName) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = dirPath + File.separator + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
