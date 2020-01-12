package animeAndManga.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "changes")
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_id")
    private Long id;

    @Column(name = "entity")
    private String entity;

    @Column(name = "inentity_id")
    private long inentityId;

    @Column(name="change_json")
    private String changeJson;

    @Column(name="change_type")
    private String changeType;

    public Change(String entity, long inentityId, String changeType, String changeJson) {
        this.entity = entity;
        this.inentityId = inentityId;
        this.changeJson = changeJson;
        this.changeType = changeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getInentityId() {
        return inentityId;
    }

    public void setInentityId(long inentityId) {
        this.inentityId = inentityId;
    }

    public String getChangeJson() {
        return changeJson;
    }

    public void setChangeJson(String changeJson) {
        this.changeJson = changeJson;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
}
