package net.xunto.roleplaychat.fabric;

import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.xunto.roleplaychat.framework.text.Text;
import net.xunto.roleplaychat.framework.text.TextColor;
import net.xunto.roleplaychat.framework.text.TextComponent;

public class FabricComponents {

  /**
   * Converts a TextColor to its corresponding Formatting in Minecraft.
   *
   * @param color The input TextColor to be converted
   * @return The corresponding Formatting in Minecraft If the input color does not have a
   * corresponding Formatting, returns Formatting.WHITE
   */
  private static Formatting toMinecraftFormatting(TextColor color) {
    Formatting formatting = Formatting.byName(color.name());

    if (formatting != null) {
      return formatting;
    }

    return Formatting.WHITE;
  }

  public static MutableText createComponent(String content, TextColor color) {
    return net.minecraft.text.Text.literal(content).formatted(toMinecraftFormatting(color));
  }

  /**
   * Converts a TextComponent to a MutableText in Minecraft.
   *
   * @param component The input TextComponent to be converted
   * @return A MutableText object representing the input TextComponent If the input component has
   * hover text, the returned MutableText will have it set as well
   */
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

  /**
   * Adds hover text to a MutableText object.
   *
   * @param mcComponent The MutableText object to add the hover text to
   * @param hoverText   The hover text to be added
   * @return The MutableText object with the hover text set
   */
  private static MutableText addHoverText(MutableText mcComponent, Text hoverText) {
    return mcComponent.setStyle(
        mcComponent.getStyle().withHoverEvent(
            new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                toTextComponent(hoverText)
            )
        )
    );
  }

  /**
   * Converts a Text object to a MutableText object. The conversion result is cached in the Text
   * object for future use.
   *
   * @param text The input Text object to be converted
   * @return A MutableText object representing the input Text object
   */
  public static MutableText toTextComponent(Text text) {
    // Check if the input text has a cache of a MutableText object
    MutableText cachedComponent = getCachedTextComponent(text);
    if (cachedComponent != null) {
      return cachedComponent;
    }

    // Create a MutableText object from the input text's components
    MutableText result = createTextComponentFromText(text);

    // Cache the result for future use
    text.setCache(result);
    return result;
  }

  /**
   * Gets the cached MutableText object from a Text object.
   *
   * @param text The Text object to get the cached MutableText from
   * @return The cached MutableText object, or null if it doesn't exist
   */
  private static MutableText getCachedTextComponent(Text text) {
    Object cache = text.getCache();
    if (cache instanceof MutableText) {
      return (MutableText) cache;
    }

    return null;
  }

  /**
   * Creates a MutableText object from a Text object.
   *
   * @param text The input Text object to create the MutableText from
   * @return A MutableText object representing the input Text object
   */
  private static MutableText createTextComponentFromText(Text text) {
    MutableText result = createComponent("", text.getDefaultColor());

    for (TextComponent component : text.getComponents()) {
      result.append(convertComponent(component));
    }

    return result;
  }
}
