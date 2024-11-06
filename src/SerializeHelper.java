import java.io.*;

/**
 * @author Sean Grimes, sean@seanpgrimes.com
 *
 * Serialize and deserialize any type of object that implements the serializable
 * interface.
 */
public class SerializeHelper {

    /**
     * This is a generic deserialization method, it only needs to know the type of
     * object it is deserializing. It will work for *anything* that can be deserialized.
     * @param type The object type, passed as, for example, Object.class
     * @param path The path to the file to be deserialized
     * @param <T> A necessary generic qualifier, it is implicitly passed
     * @return The deserialized object
     * NOTE: This would be called like so:
     * SerializationHelper.deserialize(Object.class, path) where "Object.class" is
     * whatever object type you're deserializing, e.g. "vehicle.Car.class" to deserialize the vehicle.Car
     */
    public static <T> T deserialize(Class<T> type, String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException(path + " is invalid");
        }

        T deserializedObject = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            deserializedObject = type.cast(ois.readObject());

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing file: " + e.getMessage());
        }
        return deserializedObject;
    }

    /**
     * Serialize a class to disk using generics and passed class attributes.
     * @param type The object type, passed as, for example, Object.class
     * @param obj The object to be saved, will be up-cast to the proper type before saving
     * @param dirPath The path to the directory to store the object
     * @param fileName The name of the file to serialize the object to
     * @param <T> A necessary generic qualifier, implicitly passed
     * @return The full path to the serialized object, stored on disk
     */
    public static <T> String serialize(Class<T> type, Object obj, String dirPath, String fileName) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fullPath = dirPath + fileName;
        try (FileOutputStream fos = new FileOutputStream(fullPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(type.cast(obj));
        } catch (IOException e) {
            System.err.println("Error serializing object: " + e.getMessage());
            return null; // Return null to indicate serialization failure
        }
        return fullPath;
    }
}
