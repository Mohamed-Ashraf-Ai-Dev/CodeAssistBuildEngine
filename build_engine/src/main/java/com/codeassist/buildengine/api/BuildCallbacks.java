package com.codeassist.buildengine.api;
import java.nio.file.Path;
public final class BuildCallbacks {
  private BuildCallbacks(){}
  public interface Progress { void onProgress(String status, int percentage); }
  public interface Completion { void onComplete(BuildResult result); }
  public static final class BuildResult { public final boolean success; public final Path output, mapping; public final String log;
    public BuildResult(boolean success, Path output, Path mapping, String log){this.success=success;this.output=output;this.mapping=mapping;this.log=log;}
  }
}
