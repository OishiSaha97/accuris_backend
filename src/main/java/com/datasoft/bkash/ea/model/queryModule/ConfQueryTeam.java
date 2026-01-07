package com.datasoft.bkash.ea.model.queryModule;

import com.datasoft.bkash.ea.model.common_properties.CommonProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class ConfQueryTeam extends CommonProperties {
    @Id
    private Integer id;
    private String objName;
    private Integer teamTypeId;
    private Integer investigationTeamId;
    private String division;
    private String department;
    private Boolean objStatus;
    private String description;
    private String mnemonic;
    private String responderTeam;
    /*New fields for OPERATIONAL TEAM CONFIG*/
    private Integer responder_team_id;
    private String team_mnemonic;
    private String recommendation_option;
    @Transient
    private Remark remark;
    private Integer divisionId;
    private Integer depertmentId;
    private String email;

}
