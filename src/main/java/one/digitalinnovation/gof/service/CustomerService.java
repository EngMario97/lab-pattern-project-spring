
package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Customer;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author EngMario97
 */
public interface CustomerService {

	Iterable<Customer> searchAll();

	Customer searchById(Long id);

	void insert(Customer customer);

	void update(Long id,Customer customer);

	void delete(Long id);

}
