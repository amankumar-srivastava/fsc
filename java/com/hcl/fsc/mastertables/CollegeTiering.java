package com.hcl.fsc.mastertables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Master_College_Tiering")
public class CollegeTiering {
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer uid;
	@Id
	private String key;
	private String value;

//	@GenericGenerator(name = "wikiSequenceGenerator5", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
//
//			@Parameter(name = "sequence_name", value = "master_college_tiering_seq"),
//			@Parameter(name = "initial_value", value = "6"), @Parameter(name = "increment_size", value = "1") })

}
