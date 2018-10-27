package br.com.produtoToy.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProdutoResourceTest {
	
	@Autowired
	public WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	
	}
	
	@Test
	public void testa01RequisicaoIdSucesso() throws Exception {
		String url = "/produtos/1";
		this.mvc.perform(get(url))
		.andExpect(status().isOk())
		.andExpect((ResultMatcher) jsonPath("descricao", equalTo("Sandalia Havaiana")));
		

	}
	
	@Test
	public void testa02RequisicaoIdFalha() throws Exception {
		String url = "/produtos/3";
		this.mvc.perform(get(url))
		.andExpect(status().isNotFound());

	}
	
	@Test
	public void testa03RequisicaoDescricaoSucesso() throws Exception {
		String url = "/produtos/like/havaianas";
		this.mvc.perform(get(url))
		.andExpect(status().isNotFound())
		.andExpect((ResultMatcher) content().string(containsString("Sandalia Havaiana")));

	}

	@Test
	public void testa04RequisicaoDescricaoFalha() throws Exception {
		String url = "/produtos/like/havaianas";
		this.mvc.perform(delete(url))
		.andExpect(status().isNotFound());

	}

	@Test
	public void testa05RequisicaoDeleteSucesso() throws Exception {
		String url = "/produtos/1";
		this.mvc.perform(delete(url))
		.andExpect(status().isOk())
		.andExpect((ResultMatcher) content().string(containsString("1")));
		

	}
	
	@Test
	public void testa06RequisicaoDeleteFalha() throws Exception {
		String url = "/produtos/3";
		this.mvc.perform(delete(url))
		.andExpect(status().isNotFound());

	}
	
	@Test
	public void testa07RequisicaoPostSucesso() throws Exception {
		String url = "/produtos";
		this.mvc.perform(post(url)
		.content("{\"descricao\": \"Brinquedo\"}")
		.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", is("http://localhost/produto/3")))
		.andDo(MockMvcResultHandlers.print());
		

	}
	
	@Test
	public void testa08RequisicaoPostFalha() throws Exception {
		String url = "/produtos";
		this.mvc.perform(post(url)
		.content("{\"descricao\": \"Brinquedo\"}")
		.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
		

	}
	
	@Test
	public void testa09RequisicaoPutSucesso() throws Exception {
		String url = "/produtos";
		this.mvc.perform(put(url)
		.content("{\"id\":3, \"descricao\": \"Sandalia Teste\"}")
		.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent())
		.andDo(MockMvcResultHandlers.print());
		

	}
	
	@Test
	public void testa10RequisicaoPutFalha() throws Exception {
		String url = "/produtos";
		this.mvc.perform(put(url)
		.content("{\"id\":777, \"descricao\": \"Sandalia Teste\"}")
		.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
		

	}
	
	@Test
	public void testa10RequisicaoPutFalhaDescricao() throws Exception {
		String url = "/produtos";
		this.mvc.perform(put(url)
		.content("{\"id\":3, \"descricao\": \"Sandalia Teste\"}")
		.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andDo(MockMvcResultHandlers.print());
		

	}
}
