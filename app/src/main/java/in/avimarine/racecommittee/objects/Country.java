package in.avimarine.racecommittee.objects;

import java.util.Objects;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
public class Country {
  public String name;
  public String code;

  public Country(String countryCode, String countryName) {
    this.name = countryName;
    this.code = countryCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Country)) {
      return false;
    }
    Country country = (Country) o;
    return Objects.equals(name, country.name) &&
        Objects.equals(code, country.code);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, code);
  }
}
