package ch.thymian18.jobtracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String position;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDate appliedDate;

    @Column(columnDefinition = "TEXT")  // tells Hibernate to not create the default column datatype ("varchar(255)" -> max. 255 chars long) for String, but use datatype "Text" instead
    private String jobPostingText;

    // orphanRemoval ensure that in case a StatusHistory instance is deleted from the list, it is actually removed from the DB, not only the connection to it
    // cascade ensures that if we add an Application with statusHistory, all statusHistory entries are created in the DB too; and similarly with deletion.
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusHistory> statusHistory = new ArrayList<>();
}
