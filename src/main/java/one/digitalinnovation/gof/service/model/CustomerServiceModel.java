package one.digitalinnovation.gof.service.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Customer;
import one.digitalinnovation.gof.model.CustomerRepository;
import one.digitalinnovation.gof.model.Address;
import one.digitalinnovation.gof.model.AddressRepository;
import one.digitalinnovation.gof.service.CustomerService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class CustomerServiceModel implements CustomerService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Customer> searchAll() {
		// Buscar todos os Clientes.
		return customerRepository.findAll();
	}

	@Override
	public Customer searchById(Long id) {
		// Buscar Cliente por ID.
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.get();
	}

	@Override
	public void insert(Customer customer) {
		saveCustomerWithCep(customer);
	}

	@Override
	public void update(Long id, Customer customer) {
		// Buscar Cliente por ID, caso exista:
		Optional<Customer> customerDB = customerRepository.findById(id);
		if (customerDB.isPresent()) {
			saveCustomerWithCep(customer);
		}
	}

	@Override
	public void delete(Long id) {
		// Deletar Cliente por ID.
		customerRepository.deleteById(id);
	}

	private void saveCustomerWithCep(Customer customer) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = customer.getAddress().getCep();
		Address address = addressRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Address newAddress = viaCepService.queryCep(cep);
			addressRepository.save(newAddress);
			return newAddress;
		});
		customer.setAddress(address);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		customerRepository.save(customer);
	}

}