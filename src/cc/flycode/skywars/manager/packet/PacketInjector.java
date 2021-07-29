package cc.flycode.skywars.manager.packet;

import cc.flycode.skywars.manager.packet.handles.ChatMessageHandle;

/**
 * Created by FlyCode on 10/08/2019 Package cc.flycode.skywars.manager.packet
 */
public class PacketInjector {
    public PacketInjector() {
        new ChatMessageHandle();
    }
}
