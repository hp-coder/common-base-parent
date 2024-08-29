package com.hp.common.base.facade.repository;

import cn.hutool.core.collection.CollUtil;
import com.hp.common.base.aggreateroot.AggregateRoot;
import com.hp.common.base.facade.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCommandRepository<AGG_ROOT extends AggregateRoot<? extends AggregateRoot<?>>> {

    private final EventPublisher eventPublisher;

    protected void beforeSync(AGG_ROOT aggRoot) {
    }

    protected void afterSync(AGG_ROOT aggRoot) {
        Optional.ofNullable(aggRoot)
                .ifPresent(root -> {
                    try {
                        if (CollUtil.isEmpty(root.domainEvents())) {
                            return;
                        }
                        root.domainEvents().forEach(eventPublisher::publishEvent);
                    } catch (Exception e) {
                        log.error("Failed at publishing domain events of aggregate root={}", aggRoot.getClass().getName(), e);
                    } finally {
                        root.clearDomainEvents();
                    }
                });
    }

}
