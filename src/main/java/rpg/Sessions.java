package rpg;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Sessions {
        private static final String saveDir = "saves/";
        private static final String fileExtension = ".json";

        public static List<String> listSessionsNames() {
                List<String> sessions = new ArrayList<>();

                File directory = getDirectory();

                String[] files = directory.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                                return name.endsWith(fileExtension);
                        }
                });

                if (files != null && files.length > 0) {
                        for (String file : files) {
                                String sessionName = file.substring(0, file.lastIndexOf('.'));
                                sessions.add(sessionName);
                        }
                }

                return sessions;
        }

        public static String save(Game game) {
                File directory = getDirectory();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                try {
                        objectMapper.writeValue(new File(
                                        directory + "/" + game.listCharacters().get(0).getName() + fileExtension),
                                        game);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return "Game saved successfully!";
        }

        public static Game load(String playerName) {
                File directory = getDirectory();
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                        return objectMapper.readValue(new File(directory + "/" + playerName + fileExtension),
                                        Game.class);
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
        }

        private static File getDirectory() {
                File directory = new File(saveDir);

                if (!directory.exists()) {
                        directory.mkdirs();
                }
                return directory;
        }
}