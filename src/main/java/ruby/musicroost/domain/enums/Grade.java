package ruby.musicroost.domain.enums;

import lombok.Getter;

@Getter
public enum Grade {
    BEGINNER, INTERMEDIATE, ADVANCED;

    public static Grade parseGrade(String name) {
        if (name == null || name.isEmpty()) return null;
        return Grade.valueOf(name);
    }
}
