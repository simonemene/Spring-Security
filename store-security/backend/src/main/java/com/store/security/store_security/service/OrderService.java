package com.store.security.store_security.service;

import com.store.security.store_security.entity.*;
import com.store.security.store_security.entity.key.OrderLineKeyEmbeddable;
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
			new ArticleException(String.format("[USER: %s] Article not found %s",username,articleId)));
		UserEntity user = userRepository.findByUsername(username).orElseThrow(()->new UserException(String.format("User not found: %s",username)));
		StockEntity stockEntity = stockRepository.findByArticle(articleEntity).orElseThrow(()->
				new StockException(String.format("[USER: %s] Article %s not found in stock",username,articleId)));
		if(articleEntity.getId()>0 && user.getId()>0 && stockEntity.getId()>0 && stockEntity.getQuantity()>0) {
			OrderEntity orderEntity = OrderEntity.builder().user(user)
					.tmstInsert(LocalDateTime.now()).build();
			OrderEntity resultOrder = orderRepository.save(orderEntity);
			if(resultOrder.getId()>0) {
				OrderLineKeyEmbeddable orderLineKeyEmbeddable =
						OrderLineKeyEmbeddable.builder().idOrder(resultOrder.getId()).idArticle(articleEntity.getId()).build();
				OrderLineEntity orderLineEntity = OrderLineEntity.builder().id(orderLineKeyEmbeddable)
						.order(orderEntity).article(articleEntity).build();
				OrderLineEntity result = orderLineRepository.save(orderLineEntity);
				return result.getId() != null;
			}else
			{
				throw new OrderException(String.format("[USER: %s] Order not created for article %s",username,articleId));
			}
		}
		return false;
	}
}
