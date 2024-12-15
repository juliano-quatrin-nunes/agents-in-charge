import { Field, Form, Formik } from "formik";
import { Button } from "./ui/Button";

interface RefetchIntervalSetterProps {
  refetchInterval: number;
  setRefetchInterval: (value: number) => void;
}

export const RefetchIntervalSetter = ({
  refetchInterval,
  setRefetchInterval,
}: RefetchIntervalSetterProps) => {
  return (
    <Formik
      initialValues={{ refetchInterval }}
      onSubmit={(values) => {
        setRefetchInterval(values.refetchInterval);
      }}
    >
      <Form className="flex flex-col gap-3 w-1/2">
        <label htmlFor="refetchInterval" className="text-lg font-semibold">
          Refetch Interval (ms)
        </label>
        <Field
          className="shadow-md border rounded-md p-2"
          type="number"
          name="refetchInterval"
        />
        <Button type="submit">Set Refetch Interval</Button>
      </Form>
    </Formik>
  );
};
