package com.values.game;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<FacingComponent> face = ComponentMapper.getFor(FacingComponent.class);
    public static final ComponentMapper<MeleeComponent> melee = ComponentMapper.getFor(MeleeComponent.class);
    public static final ComponentMapper<MoveComponent> move = ComponentMapper.getFor(MoveComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<CollisionComponent> collision =
            ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<EnemyComponent> enemy = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<StateComponent> state = ComponentMapper.getFor(StateComponent.class);
}
