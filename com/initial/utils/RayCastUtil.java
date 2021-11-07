package com.initial.utils;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import optifine.*;
import java.util.*;
import net.minecraft.util.*;

public class RayCastUtil
{
    private static final Minecraft mc;
    
    public static Entity rayCast(final double range, final float yaw, final float pitch) {
        final double d2;
        final double d0 = d2 = range;
        final Vec3 vec3 = RayCastUtil.mc.thePlayer.getPositionEyes(1.0f);
        boolean flag = false;
        final boolean flag2 = true;
        if (d0 > 3.0) {
            flag = true;
        }
        final Vec3 vec4 = getVectorForRotation(pitch, yaw);
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
        Entity pointedEntity = null;
        Vec3 vec6 = null;
        final float f = 1.0f;
        final List<Entity> list = RayCastUtil.mc.theWorld.getEntitiesInAABBexcluding(RayCastUtil.mc.getRenderViewEntity(), RayCastUtil.mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(f, f, f), (Predicate<? super Entity>)Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d3 = d2;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity1 = list.get(i);
            final float f2 = entity1.getCollisionBorderSize();
            final AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f2, f2, f2);
            final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
            if (axisalignedbb.isVecInside(vec3)) {
                if (d3 >= 0.0) {
                    pointedEntity = entity1;
                    vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                    d3 = 0.0;
                }
            }
            else if (movingobjectposition != null) {
                final double d4 = vec3.distanceTo(movingobjectposition.hitVec);
                if (d4 < d3 || d3 == 0.0) {
                    boolean flag3 = false;
                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        flag3 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    }
                    if (entity1 == RayCastUtil.mc.getRenderViewEntity().ridingEntity && !flag3) {
                        if (d3 == 0.0) {
                            pointedEntity = entity1;
                            vec6 = movingobjectposition.hitVec;
                        }
                    }
                    else {
                        pointedEntity = entity1;
                        vec6 = movingobjectposition.hitVec;
                        d3 = d4;
                    }
                }
            }
        }
        return pointedEntity;
    }
    
    public static Vec3 getVectorForRotation(final float pitch, final float yaw) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3(f2 * f3, f4, f * f3);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
