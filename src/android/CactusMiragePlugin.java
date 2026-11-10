package com.cactus.mirage;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CactusMiragePlugin extends CordovaPlugin {
    
    static {
        // Load the Cactus C++ Native Shared Library
        System.loadLibrary("cactus_engine");
    }

    // Native C++ JNI interface signature
    public native String nativeDescribe(String imagePath, String optionsJson);

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("describe".equals(action)) {
            String imagePath = args.getString(0);
            JSONObject options = args.optJSONObject(1);

            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        // Invoke native C++ inference on device NPU/CPU
                        String resultJson = nativeDescribe(imagePath, options != null ? options.toString() : "{}");
                        callbackContext.success(resultJson);
                    } catch (Exception e) {
                        callbackContext.error("Inference Error: " + e.getMessage());
                    }
                }
            });
            return true;
        }
        return false;
    }
}
