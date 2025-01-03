 package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

 @TeleOp
public class ClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motorAngle1 = hardwareMap.dcMotor.get("motorAngle1");
        DcMotor motorAngle2 = hardwareMap.dcMotor.get("motorAngle2");
        DcMotor motorLiftForward = hardwareMap.dcMotor.get("motorExtension1");
        DcMotor motorLiftReverse = hardwareMap.dcMotor.get("motorExtension2");

        motorLiftForward.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorLiftReverse.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");
        Servo claw = hardwareMap.servo.get("claw");
        motorLiftForward.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLiftReverse.setDirection(DcMotorSimple.Direction.REVERSE);

        motorAngle1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorAngle2.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        claw.setPosition(0);
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            if(gamepad1.a){
                telemetry.addLine("Lift Motor Going Forward");
                motorLiftForward.setPower(1);
                motorLiftReverse.setPower(1);
            } else {
                motorLiftForward.setPower(0);
                motorLiftReverse.setPower(0);
                motorLiftForward.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                motorLiftReverse.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            if(gamepad1.b){
                telemetry.addLine("Lift Motor Going Reverse");
                motorLiftForward.setPower(-1);
                motorLiftReverse.setPower(-1);
            } else {
                motorLiftForward.setPower(0);
                motorLiftReverse.setPower(0);
                motorLiftForward.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                motorLiftReverse.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            if(gamepad1.x){
                telemetry.addLine("Angle Motor Going Forward");
                motorAngle1.setPower(1);
                motorAngle2.setPower(1);
            } else{
                motorAngle1.setPower(0);
                motorAngle2.setPower(0);
            }

            if(gamepad1.y){
                telemetry.addLine("Angle Motor Going Reverse");
                motorAngle1.setPower(-1);
                motorAngle2.setPower(-1);
            } else{
                motorAngle1.setPower(0);
                motorAngle2.setPower(0);
            }

            if(gamepad1.left_trigger >= 0.75){
                claw.setPosition(1);
            } else{
                claw.setPosition(0.5);
            }
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
                double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
                double rx = gamepad1.right_stick_x;

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y + x + rx) / denominator;
                double backLeftPower = (y - x + rx) / denominator;
                double frontRightPower = (y - x - rx) / denominator;
                double backRightPower = (y + x - rx) / denominator;

                frontLeftMotor.setPower(frontLeftPower);
                backLeftMotor.setPower(backLeftPower);
                frontRightMotor.setPower(frontRightPower);
                backRightMotor.setPower(backRightPower);

            }
    }
}


