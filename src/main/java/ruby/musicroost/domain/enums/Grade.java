package ruby.musicroost.domain.enums;

import lombok.Getter;

@Getter
public enum Grade {
    BEGINNER(150000), INTERMEDIATE(180000), ADVANCED(200000);

    int cost;

    Grade(int cost) {
        this.cost = cost;
    }

    public static Grade parseGrade(String name) {
        if (name == null || name.isEmpty()) return null;
        return Grade.valueOf(name);
    }
}
