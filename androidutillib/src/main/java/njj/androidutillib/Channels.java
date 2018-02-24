public class Channels {  
  
    private static final String CHANNEL = "META-INF/channel";  
  
    private static String channel = "";  
  
    public static String getChannel() {  
        return getChannel(BDApplication.getApplication());  
    }  
  
    public static String getChannel(Context context) {  
        if (TextUtils.isEmpty(channel)) {  
            channel = getChannelInner(context);  
        }  
  
        Log.i("niejianjian", "Channels -> channel = " + channel + "VersionName = " + BuildConfig.VERSION_NAME);  
  
        return channel;  
    }  
  
    public static String getChannelInner(Context context) {  
        ApplicationInfo appInfo = context.getApplicationInfo();  
        String sourceDir = appInfo.sourceDir;  
        String ret = "";  
        ZipFile zipfile = null;  
        try {  
            zipfile = new ZipFile(sourceDir);  
            Enumeration<?> entries = zipfile.entries();  
            while (entries.hasMoreElements()) {  
                ZipEntry entry = ((ZipEntry) entries.nextElement());  
                String entryName = entry.getName();  
                if (entryName.startsWith(CHANNEL)) {  
                    ret = entryName;  
                    break;  
                }   
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (zipfile != null) {  
                try {  
                    zipfile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
  
        String[] split = ret.split("_");  
        if (split.length >= 2) {  
            return split[1];  
        } else {  
            return "test";  
        }  
    }  
}  
