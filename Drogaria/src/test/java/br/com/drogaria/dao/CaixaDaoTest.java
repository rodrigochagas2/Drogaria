package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Caixa;

public class CaixaDaoTest {
	
	
	@Test
	@Ignore
	public void salvar() throws ParseException {		
		
		Caixa caixa = new Caixa();
		CaixaDao caixaDao = new CaixaDao();
		
		caixa.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse("21/08/2018"));		
		caixa.setValorAbertura(new BigDecimal("100.00"));
		
		caixaDao.salvar(caixa);
	}
	
	@Test
	
	public void buscar() throws ParseException {
		
		CaixaDao caixaDao = new CaixaDao();
		Caixa caixa = new Caixa();
		
		caixa = caixaDao.buscar(new SimpleDateFormat("dd/MM/yyyy").parse("19/08/2018"));
		System.out.println(caixa);
		Assert.assertNull(caixa);
		
	}

}
