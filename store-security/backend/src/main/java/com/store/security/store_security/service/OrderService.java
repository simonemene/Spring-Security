package com.store.security.store_security.service;

import com.store.security.store_security.entity.*;
import com.store.security.store_security.exceptions.ArticleException;
import com.store.security.store_security.exceptions.OrderException;
import com.store.security.store_security.exceptions.StockException;
import com.store.security.store_security.exceptions.UserException;
import com.store.security.store_security.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService{

	private final OrderRepository orderRepository;

	private final ArticleRepository articleRepository;

	private final StockRepository stockRepository;

	private final UserRepository userRepository;

	private final OrderLineRepository orderLineRepository;

	@Transactional
	@Override
	public boolean addOrder(int articleId, String username) throws OrderException {

		ArticleEntity articleEntity = articleRepository.findById(articleId).orElseThrow(()->
			new ArticleException("Article not found"));
		UserEntity user = userRepository.findByUsername(username).orElseThrow(()->new UserException("User not found"));
		StockEntity stockEntity = stockRepository.findByArticle(articleEntity).orElseThrow(()->new StockException("Article not found in stock"));
		if(articleEntity.getId()>0 && user.getId()>0 && stockEntity.getId()>0) {
			OrderEntity orderEntity = OrderEntity.builder().user(user)
					.tmstInsert(LocalDateTime.now()).build();
			OrderEntity resultOrder = orderRepository.save(orderEntity);
			if(resultOrder.getId()>0) {
				OrderLineEntity orderLineEntity = OrderLineEntity.builder()
						.order(orderEntity).article(articleEntity).build();
				OrderLineEntity result = orderLineRepository.save(orderLineEntity);
				return result.getId() != null;
			}else
			{
				throw new OrderException("Order not created");
			}
		}
		return false;
	}
}
