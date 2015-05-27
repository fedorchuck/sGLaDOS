package io.github.fedorchuck.sglados_server.view;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by v on 26.05.2015.
 */
public class Console {
    public static void run(String[] args) {
        try {
            //Logger log = LoggerFactory.getLogger(Console.class);
            ConsoleReader reader = new ConsoleReader();

            reader.setPrompt("> ");

            //Reflections reflections = new Reflections("io.github.fedorchuck.sglados_server.impl");

            //Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CommandName.class);


            List<String> strings = new ArrayList<String>();
            List<String> help = new ArrayList<String>();
            String command = "";
            String doc;
            /*for (Class<?> aClass : annotated) {
                command = ((CommandName) aClass.getAnnotations()[0]).value();
                strings.add(command);

                doc = "";
                if (aClass.getAnnotations().length>1)
                    doc = ((CommandDoc) aClass.getAnnotations()[1]).value();

                help.add("-\t" + command + "\n" + doc + "\n");
            }*/

            List<Completer> completors = new LinkedList<Completer>();
            completors.add(new StringsCompleter(strings));


            for (Completer c : completors) {
                reader.addCompleter(c);
            }

            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            while ((line = reader.readLine()) != null) {
//            for (line = reader.readLine(); line != null; line = reader.readLine()){

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    //scheduler.shutdown();
                    //TODO: stop ActiveMQ
                    Runtime.getRuntime().exit(0);
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                    continue;
                }

                if (line.equalsIgnoreCase("help")) {
                    for (String s : help) out.println(s);
                    continue;
                }

                //out.println("======>\"" + line + "\"");
                //line += " ";
                try {
                    String commandName = "";
                    String commandParam = "null";

                    if (line.indexOf(' ') > -1) {
                        commandName = line.trim().substring(0, line.trim().indexOf(' '));
                        commandParam = line.trim().substring(line.trim().indexOf(' ') + 1);
                    } else {
                        commandName = line;
                    }
//                    System.out.println(">>>>>>>>>>>>>>>>>" + commandName + " " + commandParam);
                    out.flush(); }
                catch (Exception/*StringIndexOutOfBoundsException*/ e) {
                    System.out.println("INCORRECT COMMAND\n");
                    e.printStackTrace();
                    continue;
                }
            }
            System.out.println("Good bye!");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
