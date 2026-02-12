package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "remarks")
public class Remark {
    @PositiveOrZero
    private final int id;

    @NotBlank(message = "Remark Item must be defined")
    @Size(max = 5, message = "Remark Item must be max 5 characters")
    private final String item;

    @Size(max = 255, message = "Remark Text must be max 255 characters")
    private final String text;

    public Remark(int id, String item, String text) {
        this.id = id;
        this.item = item;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Remark{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
