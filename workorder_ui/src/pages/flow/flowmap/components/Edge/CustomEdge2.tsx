import { FC } from 'react';
import { EdgeProps, getBezierPath, EdgeText } from 'reactflow';

const CustomEdge: FC<EdgeProps> = ({
                                     id,
                                     sourceX,
                                     sourceY,
                                     targetX,
                                     targetY,
                                     sourcePosition,
                                     targetPosition,
                                     markerEnd,
                                     data,
                                   }) => {
  const [edgePath, labelX, labelY] = getBezierPath({
    sourceX,
    sourceY,
    sourcePosition,
    targetX,
    targetY,
    targetPosition,
  });

  return (
    <>
      <path id={id} className="react-flow__edge-path" d={edgePath} markerEnd={markerEnd}/>
      <EdgeText
        x={labelX}
        y={labelY}
        label={data.text}
        labelStyle={{ fill: 'white' }}
        labelShowBg
        labelBgStyle={{ fill: 'red' }}
        labelBgPadding={[2, 4]}
        labelBgBorderRadius={2}
        onClick={() => console.log(data)}
      />
      ;
    </>
  );
};

export default CustomEdge;
