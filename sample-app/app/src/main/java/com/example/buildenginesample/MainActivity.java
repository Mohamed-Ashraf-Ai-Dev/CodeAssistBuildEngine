package com.example.buildenginesample;
import android.app.Activity;import android.os.Bundle;import com.codeassist.buildengine.BuildManager;import com.codeassist.buildengine.api.*;import java.nio.file.*;
public class MainActivity extends Activity { @Override public void onCreate(Bundle b){super.onCreate(b); Path source=getFilesDir().toPath().resolve("source-project"); BuildRequest r=BuildRequest.builder().projectPath(source).buildType(BuildType.DEBUG_APK).outputPath(getFilesDir().toPath().resolve("out.apk")).build(); new BuildManager(this).buildAsync(r,(s,p)->{},result->{}); } }
