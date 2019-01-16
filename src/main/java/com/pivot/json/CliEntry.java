package com.pivot.json;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author xianfengzhang
 * @date 2019/1/14
 */
public class CliEntry {
    private static final String END = "^";

    private void run() {
        Scanner c = new Scanner(System.in);
        JsonBuilder jb = getJsonBuilder(c);
        String line = "";
        boolean running = true;
        while (running) {
            System.out.println("请输入要执行的命令:i->查询,g 类名->生成代码并显示,f 文件名->生成代码并写入文件,^->退出");
            line = c.nextLine();
            String[] la = line.split(" ");
            String cmd = la[0];
            switch (cmd) {
                case "i": {
                    interactive(c, jb);
                }
                break;
                case "g": {
                    genCmd(jb, la);
                }
                break;
                case "f": {
                    fileCmd(jb, la);
                }
                break;
                case "^": {
                    running = false;
                }
                break;
                default:
                    break;
            }

        }
    }
    private void fileCmd(JsonBuilder jb, String[] la) {
        try {
            String cn = "Cn";
            if (la.length > 1) {
                cn = la[1];
            }
            String r = jb.toSrc(cn);
            Path path = Paths.get(cn + ".java");
            Files.write(path, r.getBytes());
            System.out.println("写入成功:" + path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void genCmd(JsonBuilder jb, String[] la) {
        String cn = "Cn";
        if (la.length > 1) {
            cn = la[1];
        }
        String r = jb.toSrc(cn);
        System.out.println(r);
    }

    private void interactive(Scanner c, JsonBuilder jb) {
        String opt = "";
        while (true) {
            System.out.println("请输入查询的键(q-退出查询):");
            opt = c.nextLine();
            if ("q".equals(opt)) {
                break;
            }
            Object r = jb.get(opt);
            System.out.println(opt + "==" + r);
        }
    }

    private JsonBuilder getJsonBuilder(Scanner scan) {
        System.out.println("请输入json串(^ 回车结束):");
        StringBuilder sb = new StringBuilder();
        String line = "";
        while (!END.equals(line = scan.nextLine())) {
            sb.append(line);
        }
        JsonBuilder jb = AntlrJsonBuilder.build(sb.toString());
        jb.digIn();
        return jb;
    }

    public static void main(String[] args) {
        new CliEntry().run();
    }
}
