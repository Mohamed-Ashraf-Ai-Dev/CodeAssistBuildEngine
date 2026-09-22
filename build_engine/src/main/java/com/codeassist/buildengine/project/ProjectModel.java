package com.codeassist.buildengine.project;
import java.nio.file.*;import java.util.*;import javax.xml.parsers.*;import org.w3c.dom.*;
public final class ProjectModel {
 public final Path root, manifest, main; public final String packageName; public final int minSdk,targetSdk;
 private ProjectModel(Path r,Path m,Path main,String p,int min,int target){root=r;manifest=m;this.main=main;packageName=p;minSdk=min;targetSdk=target;}
 public static ProjectModel load(Path root)throws Exception{Path main=root.resolve("src/main"),m=main.resolve("AndroidManifest.xml");if(!Files.isRegularFile(m))throw new IllegalArgumentException("Missing src/main/AndroidManifest.xml");Document d=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(m.toFile());Element e=d.getDocumentElement();String p=e.getAttribute("package");int min=1,target=35;NodeList uses=e.getElementsByTagName("uses-sdk");if(uses.getLength()>0){Element s=(Element)uses.item(0);min=num(s.getAttribute("android:minSdkVersion"),min);target=num(s.getAttribute("android:targetSdkVersion"),target);}return new ProjectModel(root,m,main,p,min,target);}
 private static int num(String s,int d){try{return s==null||s.isEmpty()?d:Integer.parseInt(s);}catch(Exception x){return d;}}
 public List<Path> javaSources()throws Exception{return sources("java",".java");} public List<Path> kotlinSources()throws Exception{return sources("kotlin",".kt");}
 private List<Path> sources(String dir,String suffix)throws Exception{Path p=main.resolve(dir);if(!Files.exists(p))return Collections.emptyList();try(java.util.stream.Stream<Path>s=Files.walk(p)){List<Path>r=new ArrayList<>();s.filter(x->x.toString().endsWith(suffix)).forEach(r::add);return r;}}
 public Path res(){return main.resolve("res");} public Path libs(){return root.resolve("libs");}
}
