package com.samilly.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.samilly.cursomc.domain.Categoria;
import com.samilly.cursomc.dto.CategoriaDTO;
import com.samilly.cursomc.repositories.CategoriaRepository;
import com.samilly.cursomc.services.exceptions.DataIntegrityException;
import com.samilly.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired // Essa anotacao serve para instanciar uma dependencia automaticamente
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+
				", Tipo: "+Categoria.class.getName())); //Se nao achar o id vai retornar uma excecao com a mensagem passada e eo id e o nome da categoria
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); //para ter certeza que eh um objeto novo e nao uma atualizacao
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); //ja usa o metodo find pra verificar se esse objeto existe mesmo, caso contrario esse metodo ja lanca uma excecao
		return repo.save(obj); //o save serve tanto pra inserir quanto pra atualizar. A diferenca eh que quando o id ta valendo nulo, ele insere, e quando nao ta nulo, ele atualiza
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){ //Paginacao
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy); //passa quais parametros serao usados opcionalmente ou n
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) { //metodo auxiliar que instancia uma categoria a partir de um dto
		return new Categoria(objDto.getId(),objDto.getNome());
	}
	
	
}
