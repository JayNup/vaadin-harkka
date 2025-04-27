package org.hohoho.cheer.repository;

import org.hohoho.cheer.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
