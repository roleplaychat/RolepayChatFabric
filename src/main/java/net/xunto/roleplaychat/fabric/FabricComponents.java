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
    // Create a MutableText object with the content and color from the input component
    MutableText mcComponent = createComponent(
        component.getContent(),
        component.getColor()
    );

    // Check if there's hover text in the input component
    Text hoverText = component.getHoverText();
    if (hoverText != null) {
      mcComponent = addHoverText(mcComponent, hoverText);
    }

    return mcComponent;
  }

  private static MutableText addHoverText(MutableText mcComponent, Text hoverText) {
    return mcComponent.setStyle(
        mcComponent.getStyle().withHoverEvent(
            new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                toTextComponent(hoverText)
            )
        )
    );
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
