package com.codeassist.buildengine.api;
import java.nio.file.Path;
public final class BuildRequest {
  public final Path projectPath, androidJarPath, toolchainPath, outputPath, cachePath;
  public final BuildType buildType; public final SigningConfig signingConfig; public final boolean shrink;
  private BuildRequest(Builder b){projectPath=b.projectPath;androidJarPath=b.androidJarPath;toolchainPath=b.toolchainPath;outputPath=b.outputPath;cachePath=b.cachePath;buildType=b.buildType;signingConfig=b.signingConfig;shrink=b.shrink;}
  public static Builder builder(){return new Builder();}
  public static final class Builder {
    private Path projectPath,androidJarPath,toolchainPath,outputPath,cachePath; private BuildType buildType=BuildType.DEBUG_APK; private SigningConfig signingConfig; private boolean shrink=true;
    public Builder projectPath(Path v){projectPath=v;return this;} public Builder androidJarPath(Path v){androidJarPath=v;return this;} public Builder toolchainPath(Path v){toolchainPath=v;return this;}
    public Builder outputPath(Path v){outputPath=v;return this;} public Builder cachePath(Path v){cachePath=v;return this;} public Builder buildType(BuildType v){buildType=v;return this;}
    public Builder signingConfig(SigningConfig v){signingConfig=v;return this;} public Builder shrink(boolean v){shrink=v;return this;}
    /** androidJarPath/toolchainPath may be omitted when BuildManager is created with an Android Context. */
    public BuildRequest build(){if(projectPath==null)throw new IllegalStateException("projectPath is required");return new BuildRequest(this);}
  }
}
