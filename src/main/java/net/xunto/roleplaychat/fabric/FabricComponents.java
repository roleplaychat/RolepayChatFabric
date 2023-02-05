package net.xunto.roleplaychat.fabric;

import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.xunto.roleplaychat.framework.text.Text;
import net.xunto.roleplaychat.framework.text.TextColor;
import net.xunto.roleplaychat.framework.text.TextComponent;

public class FabricComponents {

  public static Formatting toMinecraftFormatting(TextColor color) {
    for (Formatting value : Formatting.values()) {
      if (value.name().equals(color.name())) {
        return value;
      }
    }

    return Formatting.WHITE;
  }

  public static MutableText createComponent(String content, TextColor color) {
    return net.minecraft.text.Text.literal(content).formatted(toMinecraftFormatting(color));
  }

  public static net.minecraft.text.Text convertComponent(TextComponent component) {
    MutableText mcComponent = createComponent(
        component.getContent(),
        component.getColor()
    );

    Text hoverText = component.getHoverText();
    if (hoverText != null) {
      mcComponent = mcComponent.setStyle(
          mcComponent.getStyle().withHoverEvent(
              new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                  toTextComponent(hoverText)
              )
          )
      );
    }

    return mcComponent;
  }

  public static MutableText toTextComponent(Text text) {
    Object cache = text.getCache();
    if (cache instanceof MutableText) {
      return (MutableText) cache;
    }

    MutableText result = createComponent("", text.getDefaultColor());

    for (TextComponent component : text.getComponents()) {
      result.append(convertComponent(component));
    }

    text.setCache(result);
    return result;
  }
}
