package one.digitalinnovation.gof.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.gof.model.Customer;
import one.digitalinnovation.gof.service.CustomerService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author EngMario97
 */
@RestController
@RequestMapping("customer")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<Iterable<Customer>> searchAll() {
		return ResponseEntity.ok(customerService.searchAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> searchById(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.searchById(id));
	}

	@PostMapping
	public ResponseEntity<Customer> insert(@RequestBody Customer customer) {
		customerService.insert(customer);
		return ResponseEntity.ok(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
		customerService.update(id, customer);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		customerService.delete(id);
		return ResponseEntity.ok().build();
	}
}