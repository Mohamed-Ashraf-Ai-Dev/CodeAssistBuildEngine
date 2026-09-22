package com.codeassist.buildengine.toolchain;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.*;import java.nio.file.*;

/** Locates host tools and can unpack the bundled assets once into app-private storage. */
public final class Toolchain {
  private final Path root; public Toolchain(Path r){root=r;}
  public Path find(String... names){for(String n:names){Path[] p={root.resolve("bin").resolve(n),root.resolve("jars").resolve(n),root.resolve("sdk").resolve(n),root.resolve(n)};for(Path x:p)if(Files.isRegularFile(x))return x;}return null;}
  public Path required(String... names){Path p=find(names);if(p==null)throw new IllegalStateException("Missing toolchain asset: "+String.join(" or ",names));return p;}
  public Path aapt2(){return required("aapt2","aapt2.exe");} public Path apksigner(){return required("apksigner.jar");} public Path d8(){return required("d8.jar");} public Path r8(){return find("r8.jar");} public Path ecj(){return required("ecj.jar");} public Path bundletool(){return required("bundletool.jar");} public Path androidJar(){return required("android.jar");}
  /** Extracts assets/toolchain to internal storage. Safe to call on every startup. */
  public static Path install(Context context)throws IOException{Path dst=context.getDir("codeassist-toolchain",Context.MODE_PRIVATE).toPath();copyTree(context.getAssets(),"toolchain",dst);Path a=dst.resolve("bin/aapt2");if(Files.exists(a))a.toFile().setExecutable(true);return dst;}
  private static void copyTree(AssetManager assets,String asset,Path dst)throws IOException{Files.createDirectories(dst);String[] children=assets.list(asset);if(children==null||children.length==0){try(InputStream in=assets.open(asset)){Files.copy(in,dst,StandardCopyOption.REPLACE_EXISTING);}return;}for(String child:children)copyTree(assets,asset+"/"+child,dst.resolve(child));}
}
