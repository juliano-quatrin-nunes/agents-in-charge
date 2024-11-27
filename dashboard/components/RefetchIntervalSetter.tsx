import { Field, Form, Formik } from "formik";

interface RefetchIntervalSetterProps {
  refetchInterval: number;
  setRefetchInterval: (value: number) => void;
}

export const RefetchIntervalSetter = ({ refetchInterval, setRefetchInterval }: RefetchIntervalSetterProps) => {
  return (
    <Formik
      initialValues={{ refetchInterval }}
      onSubmit={(values) => {
        setRefetchInterval(values.refetchInterval);
      }}
    >
      <Form className="flex flex-col gap-3">
        <Field className="shadow-sm rounded-md p-2" type="number" name="refetchInterval" />
        <button
          className="shadow-sm border bg-slate-500 hover:bg-slate-400 hover:transition-all text-white font-bold rounded-md p-2"
          type="submit"
        >
          Set Refetch Interval
        </button>
      </Form>
    </Formik>
  );
};
