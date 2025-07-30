import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { toPascalCase } from '../../utils';

interface DemoPageProps {
  children: React.ReactNode;
}

export const DemoPage = ({ children }: DemoPageProps) => {
  const { pathname } = useLocation();
  const name = pathname.slice(1);

  return (
    <div className="demo-page" data-page={name}>
      <div className="demo-header">
        <Link className="demo-header-back" to="/" aria-label="Back">
        </Link>
        <h1 className="demo-header-title">{toPascalCase(name)}</h1>
      </div>
      {children}
    </div>
  );
};
