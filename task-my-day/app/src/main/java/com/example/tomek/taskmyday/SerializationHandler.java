package com.example.tomek.taskmyday;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationHandler {
    public static String filename = "data.ser";

    public static void serializeObject(Context context, Serializable object) {
        try {
           FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
           ObjectOutputStream os = new ObjectOutputStream(fos);
           os.writeObject(object);
           os.close();
           fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static Object deserializeObject(Context context) throws IOException, ClassNotFoundException {
            Object obj = null;
            try {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream in = new ObjectInputStream(fis);
                obj = in.readObject();
                in.close();
                fis.close();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        return obj;
    }
}