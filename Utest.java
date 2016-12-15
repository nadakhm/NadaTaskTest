package NadaTesting;


import org.junit.Test;
import java.sql.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;
import junit.framework.Assert;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class Utest {

	DAOImpl DAO_new = new DAOImpl(); 

	  
  @Mock
  Connection conn;
  @Mock
  PreparedStatement psmt;
  @InjectMocks
  DAOImpl newDAO= new DAOImpl();	
  
  @Test
  public void testProductCon(){
  Product p = new Product (6);
  Assert.assertEquals(1,p.getId());
  }
  @Test
 public void testsetType(){
	  Product p= new Product(6);
	  p.setType("Fruit");
	  Assert.assertEquals("Fruit", p.getType());	  
  }
  @Test
 public void testsetManufac(){
	  Product p= new Product(6);
	  p.setManufacturer("Egypt");
	  Assert.assertEquals("Egypt",p.getManufacturer());
 }
 @Test
 public void testsetProDate(){
	 Product p= new Product(6);
	  p.setProductionDate("15-10-2016");
	  Assert.assertEquals("15-10-2016",p.getProductionDate());
 }
@Test
 public void testsetExpDate(){
	 Product p= new Product(6);
	  p.setExpiryDate("20-1-2017");
	  Assert.assertEquals("20-1-2017",p.getExpiryDate());
 }
@Test
public void InsertHappyTest() throws SQLException, DAOException{
	  when(conn.prepareStatement(anyString())).thenReturn(psmt);

  	  Product p = new Product(6);
  	  newDAO.insertProduct(p);
  	  
      ArgumentCaptor<String> stringcaptor = ArgumentCaptor.forClass(String.class);
  	  ArgumentCaptor<Integer> intcaptor = ArgumentCaptor.forClass(int.class); 
      verify(psmt, times(1)).setInt(anyInt(), intcaptor.capture());
	  verify(psmt, times(4)).setString(anyInt(), stringcaptor.capture());
	  
	  Assert.assertTrue(intcaptor.getAllValues().get(0).equals(6));
	  }
	
@Test (expected = DAOException.class)
public void ExceptionCase() throws SQLException, DAOException{
	when(conn.prepareStatement(anyString())).thenReturn(psmt);
	when(psmt.executeUpdate()).thenThrow(new SQLException());
	Product p = new Product(6);
	newDAO.insertProduct(p);
	}
@Test
public void IntegrationTest() throws SQLException, DAOException{
	Product p_new=new Product(2);
	p_new.setType("try");
	p_new.setManufacturer("trym");
	p_new.setProductionDate("20try ");
	p_new.setExpiryDate("15try");
	
	DAO_new.insertProduct(p_new);
	Assert.assertNotNull(DAO_new.getProduct(2));
	DAO_new.deleteProduct(2);
    Assert.assertNull(DAO_new.getProduct(2));
}
}
