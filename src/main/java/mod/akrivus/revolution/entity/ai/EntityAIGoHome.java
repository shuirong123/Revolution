package mod.akrivus.revolution.entity.ai;

import mod.akrivus.revolution.entity.EntityHuman;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIGoHome extends EntityAIBase {
    protected EntityHuman human;
    protected BlockPos home;
    public EntityAIGoHome(EntityHuman human) {
        this.human = human;
        this.setMutexBits(1);
    }
    @Override
    public boolean shouldExecute() {
    	if (this.human.getTribe() != null && !this.human.getTribe().homeless) {
    		this.home = this.human.getTribe().home;
    		if (this.human.world.getWorldTime() > 12000 && this.human.getDistanceSq(this.home) > 64) {
    			return true;
    		}
    	}
    	return false;
    }
    @Override
    public boolean shouldContinueExecuting() {
        return this.human.getDistanceSq(this.home) > 64 && this.human.world.getWorldTime() < 12000;
    }
    @Override
    public void startExecuting() {
    	if (this.human.getDistanceSq(this.home) > 256) {
    		Vec3d pos = RandomPositionGenerator.findRandomTargetBlockTowards(this.human, 16, 4, new Vec3d(this.home.getX(), this.home.getY(), this.home.getZ()));
            if (pos != null) {
                this.human.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1.0D);
            }
    	}
    	else {
    		this.human.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0D);
    	}
    }
    @Override
    public void resetTask() {
    	this.human.getNavigator().clearPath();
    }
}