package ruby.musicroost.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = -1571607267L;

    public static final QStudent student = new QStudent("student");

    public final EnumPath<ruby.musicroost.domain.enums.Course> course = createEnum("course", ruby.musicroost.domain.enums.Course.class);

    public final StringPath email = createString("email");

    public final EnumPath<ruby.musicroost.domain.enums.Grade> grade = createEnum("grade", ruby.musicroost.domain.enums.Grade.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final DatePath<java.time.LocalDate> since = createDate("since", java.time.LocalDate.class);

    public QStudent(String variable) {
        super(Student.class, forVariable(variable));
    }

    public QStudent(Path<? extends Student> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudent(PathMetadata metadata) {
        super(Student.class, metadata);
    }

}

