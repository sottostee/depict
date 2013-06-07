package it.unibo.deis.lia.depict.observers;

import it.unibo.deis.lia.depict.KinectModel;
import org.OpenNI.*;


public class NewUserObserver implements IObserver<UserEventArgs> {

    private KinectModel model;
    private SkeletonCapability skeletonCap;
    private PoseDetectionCapability poseDetectionCap;

    public NewUserObserver( KinectModel model, SkeletonCapability skeletonCap, PoseDetectionCapability poseDetectionCap) {
        if(model==null)
        	System.out.println("model null");
    	if(skeletonCap==null)
        	System.out.println("skeletonCap null");
        if(poseDetectionCap==null)
        	System.out.println("poseDetectionCap null");
        
    	this.model = model;
        this.skeletonCap = skeletonCap;
        this.poseDetectionCap = poseDetectionCap;
    }

    public void update(IObservable<UserEventArgs> observable,
                       UserEventArgs args)
    {
        model.newUser( args.getId() );
        try
        {
            if (skeletonCap.needPoseForCalibration())
            {
                poseDetectionCap.startPoseDetection(skeletonCap.getSkeletonCalibrationPose(), args.getId());
            }
            else
            {
                skeletonCap.requestSkeletonCalibration(args.getId(), true);
            }
        } catch (StatusException e)
        {
            e.printStackTrace();
        }
    }
}