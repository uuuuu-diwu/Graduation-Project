package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Txt2Tree {
//    private static PrintWriter System.out;
//
//    static {
//        try {
//            System.out = new PrintWriter(new FileOutputStream("E:\\IdeaProjects\\JenaDemo\\src\\Parser\\logOfParser.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    private String filePath;
    private File file;
    private ArrayList<Node> treeVec;
    private void readOffsetRest(BufferedReader br) {
        try {
            br.reset();
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return;
    }

    public Txt2Tree(String filePath) {
        file = new File(filePath);
        treeVec = new ArrayList<Node>();
    }

    public void process() throws IOException{
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while((line = br.readLine()) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()) {
                String[] tmp = line.split("\\s+");
                Node chapter = new Node(tmp[tmp.length - 1]);
                System.out.println("insert node " + chapter.getTitle() + " to treeVec");
                processDiseaseNode(chapter,br);
                treeVec.add(chapter);
            }
        }
    }

    public void processDiseaseNode(Node diseaseNode,BufferedReader br) {
        String line;
        while((line = markAndReadLine(br)) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()) {
                readOffsetRest(br);
                return;
            }
            Matcher mat = RgexPatern.knowledgePointPattern.matcher(line);
            if(mat.find()) {
                    String kp = mat.group(1);
                    Node kpNode = new Node(kp);
                    diseaseNode.insertChild(kpNode);
                    System.out.println(kp);
                    processVocabNode(kpNode,br);
            }
        }
    }

    public void processVocabNode(Node kp,BufferedReader br) {
        String line;
        while((line = markAndReadLine(br)) != null) {
            if(RgexPatern.knowledgePointPattern.matcher(line).find()||
                    RgexPatern.chapterPattern.matcher(line).find()) {
                readOffsetRest(br);
                return;
            }
            Vocab vocab = new Vocab(line,kp);
            kp.insertVocab(vocab);
            System.out.println("voca:" + line + "inserted into" + kp.getParent().getTitle() + " --> " + kp.getTitle());
        }
    }

    public String markAndReadLine(BufferedReader br) {
        try {
            br.mark(1024 * 4);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        try {
            return br.readLine();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }
    public static void main(String[] argv) {
        Txt2Tree obj = new Txt2Tree("E:\\内科临床诊疗指南.txt");
        try {
            obj.process();
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
