package cc.flycode.skywars.manager.packet.handles;

import cc.flycode.skywars.Skywars;
import cc.flycode.skywars.manager.user.User;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by FlyCode on 10/08/2019 Package cc.flycode.skywars.manager.packet.handles
 */
public class ChatMessageHandle {
    private interface Processor {
        Object process(Object value, Object parent);
    }

    public ChatMessageHandle() {

 /*       ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(Skywars.instance, PacketType.Play.Server.CHAT) {
                    private JSONParser parser = new JSONParser();

                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        StructureModifier<WrappedChatComponent> componets = packet.getChatComponents();


                        Object data = null;
                        try {
                            data = parser.parse(componets.read(0).getJson());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        boolean[] result = new boolean[1];

                        transformPrimitives(data, null, (value, parent) -> {
                            if (value instanceof String) {
                                String stripped = ChatColor.stripColor((String) value);

                                if ("http://spigotmc.org/members/83207".contains(stripped) || "https://spigotmc.org/members/83207".contains(stripped)) {
                                    result[0] = true;
                                    return ((String) value).replace("http://spigotmc.org/members/83207", "Your mom").replace("https://spigotmc.org/members/83207", "Your mom");
                                }
                            }
                            return value;
                        });

                        // Write back the changed string
                        if (result[0]) {
                            componets.write(0, WrappedChatComponent.fromJson(JSONValue.toJSONString(data)));
                        }
                    }
                });
*/
    }


    private Object transformPrimitives(Object value, Object parent, Processor processor) {
        // Check its type
        if (value instanceof JSONObject) {
            return transformPrimitives((JSONObject) value, processor);
        } else if (value instanceof JSONArray) {
            return transformPrimitives((JSONArray) value, processor);
        } else {
            return processor.process(value, parent);
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject transformPrimitives(JSONObject source, Processor processor) {
        for (Object key : source.keySet().toArray()) {
            Object value = source.get(key);
            source.put(key, transformPrimitives(value, source, processor));
        }
        return source;
    }

    @SuppressWarnings("unchecked")
    private JSONArray transformPrimitives(JSONArray source, Processor processor) {
        for (int i = 0; i < source.size(); i++) {
            Object value = source.get(i);
            source.set(i, transformPrimitives(value, source, processor));
        }
        return source;
    }
}
