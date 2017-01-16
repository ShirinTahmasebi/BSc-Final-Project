package shirin.tahmasebi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MakeMVPFiles {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        Object packageName = JOptionPane.showInputDialog(frame, "Enter package name:");
        Object functionality = JOptionPane.showInputDialog(frame, "Enter functionality name:");
        packageName = packageName.toString().toLowerCase();

        String directory = "//Users//shirin//AndroidStudioProjects//MSc-Final-Project" +
                "//app//src//main//java//shirin//tahmasebi//mscfinalproject//" + packageName;

        // If directory exists?
        if (!Files.exists(Paths.get(directory))) {
            try {
                createPackageDirectory(directory);
                createJavaFile(directory, "Activity", packageName, functionality);
                createJavaFile(directory, "Presenter", packageName, functionality);
                createJavaFile(directory, "Interactor", packageName, functionality);
                addActivityToManifest(packageName, functionality);
            } catch (IOException e) {
                // Fail to create directory
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    private static void addActivityToManifest(Object packageName, Object functionality) throws IOException {
        String manifestPath = "//Users//shirin//AndroidStudioProjects//MSc-Final-Project" +
                "//app//src//main//AndroidManifest.xml";
        String manifestTag =
                "<activity android:name=\"." + packageName + "." +
                        functionality + "Activity\" /></application>";

        String content = new String(Files.readAllBytes(Paths.get(manifestPath)));
        content = content.replace("</application>", manifestTag);
        Files.write(Paths.get(manifestPath), content.getBytes());

    }

    private static void createJavaFile(String directory,
                                       String sampleName,
                                       Object packageName,
                                       Object functionality)
            throws IOException {
        String activityContent = convertFileContent(sampleName + "Sample",
                packageName, functionality);
        Path path;
        path = Paths.get(directory + "//" + functionality + sampleName + ".java");
        Files.createFile(path);
        Files.write(path, activityContent.getBytes());
    }

    private static String convertFileContent(String fileName,
                                             Object packageName,
                                             Object functionality) throws IOException {

        String basePath = "//Users//shirin//AndroidStudioProjects//MSc-Final-Project" +
                "//makemvpfiles//src//main//java//shirin//tahmasebi";
        String packagePattern = "(?i)<(/?PackageName[^>]*)>";
        String activityPattern = "(?i)<(/?ActivityName[^>]*)>";
        String presenterPattern = "(?i)<(/?PresenterName[^>]*)>";
        String viewPattern = "(?i)<(/?ViewName[^>]*)>";
        String interactorPattern = "(?i)<(/?InteractorName[^>]*)>";
        String listenerPattern = "(?i)<(/?ListenerName[^>]*)>";

        String content = new String(Files.readAllBytes(Paths.get(basePath + "//" + fileName)));
        content = content.replaceAll(packagePattern, packageName.toString());
        content = content.replaceAll(activityPattern, functionality + "Activity");
        content = content.replaceAll(presenterPattern, functionality + "Presenter");
        content = content.replaceAll(viewPattern, functionality + "View");
        content = content.replaceAll(interactorPattern, functionality + "Interactor");
        content = content.replaceAll(listenerPattern, functionality + "Listener");
        return content;
    }

    private static void createPackageDirectory(String directory) throws IOException {
        Path path;
        path = Paths.get(directory);
        Files.createDirectory(path);
    }
}
