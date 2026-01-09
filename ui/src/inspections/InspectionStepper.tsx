import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Step from "@mui/material/Step";
import StepButton from "@mui/material/StepButton";
import Stepper from "@mui/material/Stepper";
import Typography from "@mui/material/Typography";
import React from "react";
import { useState } from "react";
import HullDataForm from "./HullDataForm";
import { InspectionProps, useUpdateInspection } from "./inspection";
import RigDataForm from "./RigDataForm";
import EngineDataForm from "./EngineDataForm";
import dayjs, { Dayjs } from "dayjs";
import { useIntlayer, useLocale } from "react-intlayer";
import { Locale } from "intlayer";
import EquipmentForm from "./EquipmentForm";
import MaritimeForm from "./MaritimeForm";
import SafetyForm from "./SafetyForm";
import { Divider } from "@mui/material";
import { useNavigate } from "@tanstack/react-router";

export default function InspectionStepper({ data }: InspectionProps) {

  const content = useIntlayer("inspections");
  const { locale } = useLocale();
  const tlds: Locale = locale == "sv-FI" ? "fi-FI" : locale;
  const navigate = useNavigate();

  /* *************************************************************************
   *
   * Insert steps and step components here
   * 
   * *********************************************************************** */
  
  const steps = [content.hull, content.rig, content.engine, content.equipment, content.maritime, content.safety];
  function getStepContent(step: number) {
    switch (step) {
      case 0:
        return <HullDataForm data={data} />;
      case 1:
        return <RigDataForm data={data} />;
      case 2:
        return <EngineDataForm data={data} />;
      case 3:
        return <EquipmentForm data={data} />;
      case 4:
        return <MaritimeForm data={data} />;
      case 5:
        return <SafetyForm data={data} />;
      default:
        throw new Error("Unknown step");
    }
  }

  const { mutateAsync: updateInspection } = useUpdateInspection(data.inspectionId);

  const [activeStep, setActiveStep] = useState(0);
  const [completed, setCompleted] = useState<{
    [k: number]: boolean;
  }>({});

  const totalSteps = () => {
    return steps.length;
  };

  const completedSteps = () => {
    return Object.keys(completed).length;
  };

  const isLastStep = () => {
    return activeStep === totalSteps() - 1;
  };

  const allStepsCompleted = () => {
    return completedSteps() === totalSteps();
  };

  const handleNext = async () => {
    const newActiveStep =
      isLastStep() && !allStepsCompleted()
        ? // It's the last step, but not all steps have been completed,
          // find the first step that has been completed
          steps.findIndex((step, i) => !(i in completed))
        : activeStep + 1;
    setActiveStep(newActiveStep);
  };

  const handleBack = async () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleStep = (step: number) => () => {
    setActiveStep(step);
  };

  const handleComplete = async () => {
    setCompleted({
      ...completed,
      [activeStep]: true,
    });
    if (allStepsCompleted()) {
      // data.completed = dayjs().format('YYYY-MM-DDTHH:mm:ssZ');
      // await updateInspection(data);
    }
    handleNext();
  };

  const handleReset = () => {
    setActiveStep(0);
    setCompleted({});
  };

  const handleDone = async () => {
    data.completed = dayjs().format('YYYY-MM-DDTHH:mm:ssZ');
    console.log('Completed:', data);
    await updateInspection(data);
    navigate({
      to: '/inspect',
      replace: true,
    });  }

  return (
    <Box sx={{ width: "100%", mt: 8 }}>
      <Stepper nonLinear activeStep={activeStep}>
        {steps.map((label, index) => (
          <Step key={label} completed={completed[index]}>
            <StepButton color="inherit" onClick={handleStep(index)}>
              {label}
            </StepButton>
          </Step>
        ))}
      </Stepper>
      <div>
        {allStepsCompleted() ? (
          <React.Fragment>
            <Typography  variant='h5' sx={{ mt: 2, mb: 1 }}>{content.inspection_completed}</Typography>
            <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
              <Button onClick={handleDone}>{content.done}</Button>
              <Box sx={{ flex: "1 1 auto" }} />
              <Button onClick={handleReset}>{content.reset}</Button>
            </Box>
          </React.Fragment>
        ) : (
          <React.Fragment>
            <Box sx={{ display: "flex", flexDirection: "row", pt: 2, mt: 8 }}>
            {getStepContent(activeStep)}
            </Box>
            <Divider sx={{ color: 'primary.main', mt: 4, mb: 1 }} />
            <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
              <Button
                color="inherit"
                disabled={activeStep === 0}
                onClick={handleBack}
                sx={{ mr: 1 }}
              >
                {content.back}
              </Button>
              <Box sx={{ flex: "1 1 auto" }} />
              <Button onClick={handleNext} sx={{ mr: 1 }}>
                {content.next}
              </Button>
              {activeStep !== steps.length &&
                (completed[activeStep] ? (
                  <Typography
                    variant="caption"
                    sx={{ display: "inline-block" }}
                  >
                    {content.step} { activeStep + 1 } {content.already_completed}
                  </Typography>
                ) : (
                  <Button onClick={handleComplete}>
                    {completedSteps() === totalSteps() - 1
                      ? content.finish
                      : content.complete_step
                      }
                  </Button>
                ))}
            </Box>
          </React.Fragment>
        )}
      </div>
    </Box>
  );
}
