package net.wasdev.wlp.gradle.plugins.utils

import java.io.File
import org.gradle.api.Project
import org.gradle.plugins.ear.EarPluginConvention
import org.gradle.api.Task
import org.gradle.plugins.ear.Ear
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.artifacts.Dependency
import org.w3c.dom.Element;
import org.apache.commons.io.FilenameUtils

import net.wasdev.wlp.common.plugins.config.LooseConfigData
import net.wasdev.wlp.common.plugins.util.PluginExecutionException
import net.wasdev.wlp.common.plugins.config.LooseApplication

public class LooseEarApplication extends LooseApplication {
    
    protected Task task;

    public LooseEarApplication(Task task, LooseConfigData config) {
        super(task.getProject().getBuildDir().getAbsolutePath(), config)
        this.task = task
    }

    public void addSourceDir() throws Exception {
        File sourceDir = new File(task.getProject().path.replace(":","") + "/" + task.getProject().getConvention().getPlugin(EarPluginConvention.class).getAppDirName())
        config.addDir(sourceDir, "/")
    }

    public void addApplicationXmlFile() throws Exception {
        String applicationName = "/" + task.getProject().getConvention().getPlugin(EarPluginConvention.class).getDeploymentDescriptor().getFileName()
        File applicationXmlFile = new File(task.getProject().path.replace(":","") + "/" + task.getProject().getConvention().getPlugin(EarPluginConvention.class).getAppDirName() + "/META-INF/" + applicationName)
        if (applicationXmlFile.exists()) {
            config.addFile(applicationXmlFile, "/META-INF/application.xml");
        }
        else {
            applicationXmlFile = new File(task.destinationDir.getParentFile().getAbsolutePath() + "/tmp/ear" + applicationName);
            config.addFile(applicationXmlFile, "/META-INF/application.xml");
        }
    }
    
    public Element addWarModule(Project proj) throws Exception {
        Element warArchive = config.addArchive("/" + proj.war.archiveName);
        proj.sourceSets.main.getOutput().getClassesDirs().each{config.addDir(warArchive, it, "/WEB-INF/classes");}
        addModules(warArchive,proj)
        return warArchive;
    }
    
    public Element addJarModule(Project proj) throws Exception {
        Element moduleArchive = config.addArchive("/" + proj.jar.archiveName);
        proj.sourceSets.main.getOutput().getClassesDirs().each{config.addDir(moduleArchive, it, "/");}
        addModules(moduleArchive, proj)
        return moduleArchive;
    }
    
    private void addModules(Element moduleArchive, Project proj) {
        for (File f : proj.jar.source.getFiles()) {
            String extension = FilenameUtils.getExtension(f.getAbsolutePath())
            switch(extension) {
                case "jar":
                case "war":
                case "rar":
                    config.addFile(moduleArchive, f, "/WEB-INF/lib/" + f.getName());
                    break
                case "MF":
                    addManifestFileWithParent(moduleArchive, f)
                    break
                default:
                    break
            }
        }
    }
    
}
