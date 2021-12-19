package weblab4.tchn11.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "wl4_entry")
public class Entry {
    @Id
    @SequenceGenerator(name = "wl4_entry_id_seq", sequenceName = "wl4_entry_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wl4_entry_id_seq")
    @JsonIgnore
    private long id;

    private double x;

    private double y;

    private double r;

    private boolean result;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private User user;

    public Entry(double x, double y, double r, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        check();
    }

    private boolean checkTriangle() {
        return x <= 0 && y <= 0 && y >= (-x - r);
    }

    private boolean checkRectangle() {
        return x <= 0 && y >= 0 && x <= r && y >= -r;
    }

    private boolean checkCircle() {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r / 4;
    }


    public void check() {
        result = checkTriangle() || checkRectangle() || checkCircle();
    }
}
